# Book Sources

## Source Policy

The app will use public domain ebook text from Project Gutenberg.

Project Gutenberg source files must be recorded clearly for transparency and future review.

The app should not imply that it is officially produced by or affiliated with Project Gutenberg.

---

## Initial Books

### 1. Anne of Green Gables

Author:
L. M. Montgomery

Source:
Project Gutenberg

Status:
To be added

Local file:
`app/src/main/assets/books/anne_of_green_gables.json`

---

### 2. Anne of Avonlea

Author:
L. M. Montgomery

Source:
Project Gutenberg

Status:
To be added

Local file:
`app/src/main/assets/books/anne_of_avonlea.json`

---

### 3. Anne of the Island

Author:
L. M. Montgomery

Source:
Project Gutenberg

Status:
To be added

Local file:
`app/src/main/assets/books/anne_of_the_island.json`

---

### 4. Anne's House of Dreams

Author:
L. M. Montgomery

Source:
Project Gutenberg

Status:
To be added

Local file:
`app/src/main/assets/books/annes_house_of_dreams.json`

---

## Book File Format

Each book should be converted into structured JSON.

Example:

```json
{
  "id": "anne_of_green_gables",
  "title": "Anne of Green Gables",
  "author": "L. M. Montgomery",
  "year": 1908,
  "source": "Project Gutenberg",
  "chapters": [
    {
      "number": 1,
      "title": "Mrs. Rachel Lynde Is Surprised",
      "content": "Chapter text here..."
    }
  ]
}
```

## Cleaning Rules

When preparing book text:

- Remove Project Gutenberg header text
- Remove Project Gutenberg footer text
- Preserve chapter titles
- Preserve paragraph breaks
- Avoid changing the literary text unnecessarily
- Do not add modern rewritten content
- Do not include copyrighted introductions, notes, or illustrations from other publishers
