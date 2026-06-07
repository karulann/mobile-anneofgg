import json
import re
from pathlib import Path


PROJECT_ROOT = Path(__file__).resolve().parents[1]

INPUT_FILE = PROJECT_ROOT / "raw_books" / "anne_of_green_gables.txt"
OUTPUT_FILE = PROJECT_ROOT / "app" / "src" / "main" / "assets" / "books" / "anne_of_green_gables.json"


BOOK_ID = "anne_of_green_gables"
BOOK_TITLE = "Anne of Green Gables"
BOOK_AUTHOR = "L. M. Montgomery"
BOOK_YEAR = 1908
BOOK_SOURCE = "Project Gutenberg"


def remove_gutenberg_header_footer(text: str) -> str:
    """
    Removes common Project Gutenberg header and footer markers.
    If markers are not found, returns the original text.
    """
    start_patterns = [
        r"\*\*\* START OF THE PROJECT GUTENBERG EBOOK ANNE OF GREEN GABLES \*\*\*",
        r"\*\*\* START OF THIS PROJECT GUTENBERG EBOOK ANNE OF GREEN GABLES \*\*\*",
    ]

    end_patterns = [
        r"\*\*\* END OF THE PROJECT GUTENBERG EBOOK ANNE OF GREEN GABLES \*\*\*",
        r"\*\*\* END OF THIS PROJECT GUTENBERG EBOOK ANNE OF GREEN GABLES \*\*\*",
    ]

    cleaned = text

    for pattern in start_patterns:
        match = re.search(pattern, cleaned, flags=re.IGNORECASE)
        if match:
            cleaned = cleaned[match.end():]
            break

    for pattern in end_patterns:
        match = re.search(pattern, cleaned, flags=re.IGNORECASE)
        if match:
            cleaned = cleaned[:match.start()]
            break

    return cleaned.strip()

def extract_chapters(text: str) -> list[dict]:
    """
    Extracts chapters using headings such as:
    CHAPTER I. Mrs. Rachel Lynde is Surprised

    Preserves blank lines so paragraph breaks remain available
    for the Android reader.
    """
    chapter_pattern = re.compile(
        r"(CHAPTER\s+[IVXLCDM]+\.?\s+.+?)(?=\n)",
        flags=re.IGNORECASE
    )

    matches = list(chapter_pattern.finditer(text))

    if not matches:
        raise ValueError("No chapters found. Check the chapter heading format in the text file.")

    # Project Gutenberg text may include chapter headings twice:
    # once in the Table of Contents and once in the actual story.
    if len(matches) == 76:
        matches = matches[38:]

    chapters = []

    for index, match in enumerate(matches):
        start = match.start()
        end = matches[index + 1].start() if index + 1 < len(matches) else len(text)

        chapter_block = text[start:end].strip("\n")

        raw_lines = chapter_block.splitlines()

        heading_index = next(
            (i for i, line in enumerate(raw_lines) if line.strip()),
            None
        )

        if heading_index is None:
            continue

        heading = raw_lines[heading_index].strip()

        # Important:
        # Do not remove blank lines from content.
        # Blank lines are needed to preserve paragraph breaks.
        content_text = "\n".join(raw_lines[heading_index + 1:])

        title = clean_chapter_title(heading)
        content = normalize_paragraphs(content_text)

        chapters.append(
            {
                "number": index + 1,
                "title": title,
                "content": content
            }
        )

    return chapters


def clean_chapter_title(heading: str) -> str:
    heading = heading.strip()

    heading = re.sub(
        r"^CHAPTER\s+[IVXLCDM]+\.?\s*",
        "",
        heading,
        flags=re.IGNORECASE
    )

    return heading.strip().title()


def normalize_paragraphs(text: str) -> str:
    text = text.replace("\r\n", "\n").replace("\r", "\n")

    # Remove Project Gutenberg italic markers such as _her_ or _must_.
    text = re.sub(r"_([^_]+)_", r"\1", text)

    # Remove spaces before line breaks.
    text = re.sub(r"[ \t]+\n", "\n", text)

    # Split into paragraphs using blank lines.
    raw_paragraphs = re.split(r"\n\s*\n+", text)

    cleaned_paragraphs = []

    for paragraph in raw_paragraphs:
        lines = [
            line.strip()
            for line in paragraph.splitlines()
            if line.strip()
        ]

        if not lines:
            continue

        # Join old fixed-width plain text lines into a normal ebook paragraph.
        cleaned_paragraph = " ".join(lines)

        # Remove accidental multiple spaces.
        cleaned_paragraph = re.sub(r"\s{2,}", " ", cleaned_paragraph)

        cleaned_paragraphs.append(cleaned_paragraph)

    return "\n\n".join(cleaned_paragraphs).strip()


def main() -> None:
    if not INPUT_FILE.exists():
        raise FileNotFoundError(f"Input file not found: {INPUT_FILE}")

    raw_text = INPUT_FILE.read_text(encoding="utf-8", errors="replace")
    cleaned_text = remove_gutenberg_header_footer(raw_text)
    chapters = extract_chapters(cleaned_text)

    book_json = {
        "id": BOOK_ID,
        "title": BOOK_TITLE,
        "author": BOOK_AUTHOR,
        "year": BOOK_YEAR,
        "source": BOOK_SOURCE,
        "chapters": chapters
    }

    OUTPUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    OUTPUT_FILE.write_text(
        json.dumps(book_json, ensure_ascii=False, indent=2),
        encoding="utf-8"
    )

    print(f"Created: {OUTPUT_FILE}")
    print(f"Chapters extracted: {len(chapters)}")


if __name__ == "__main__":
    main()