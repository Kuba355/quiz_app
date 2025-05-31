# Quiz API

REST API dla aplikacji quizowej – backend napisany w Spring Boot. Umożliwia pobieranie pytań, wysyłanie odpowiedzi i zapisywanie wyników.

---

## Endpoints

### `GET /api/quiz`

Zwraca 5 losowych pytań (z dowolnej kategorii).

**Response:**

```json
[
  {
    "id": 1,
    "content": "Stolica Polski to?",
    "options": ["Warszawa", "Kraków", "Gdańsk", "Poznań"]
  },
  ...
]
```

---

### `GET /api/quiz/category?category=NAZWA`

Zwraca 5 losowych pytań z wybranej kategorii.

**Parametry:**

* `category` – nazwa kategorii (np. `matematyka`)

**Response:** (jak wyżej)

---

### `GET /api/quiz/categories`

Zwraca listę unikalnych kategorii pytań.

**Response:**

```json
["geografia", "matematyka", "astronomia", "biologia", "chemia"]
```

---

### `POST /api/quiz/submit`

Wysyła odpowiedzi użytkownika i zwraca wynik.

**Request body:**

```json
{
  "email": "jan@example.com",  // opcjonalnie
  "answers": [
    { "questionId": 1, "selectedAnswerIndex": 0 },
    { "questionId": 2, "selectedAnswerIndex": 1 }
  ]
}
```

**Response:**

```json
{
  "result": {
    "correct": 2,
    "total": 5
  }
}
```

---

### `GET /api/quiz/results?email=EMAIL&page=NUMER`

Zwraca historię wyników danego użytkownika.

**Parametry:**

* `email` – adres e-mail
* `page` – numer strony (domyślnie `0`, po 10 wyników na stronę)

**Response:**

```json
[
  {
    "correct": 4,
    "total": 5,
    "submittedAt": "2025-05-18T13:52:12.137"
  },
  {
    "correct": 3,
    "total": 5,
    "submittedAt": "2025-05-17T15:40:02.543"
  }
]
```

---

## Uwagi

* Wszystkie odpowiedzi są w formacie JSON
* Pola `correctAnswerIndex` nigdy nie są zwracane do frontendu
* Adres e-mail jest opcjonalny – wynik zapisuje się tylko wtedy, gdy użytkownik go poda

---

## Autor

Backend API do projektu quizowego – Spring Boot, JPA, H2.

Możliwa integracja z Angularem, Reactem lub innym frontem SPA.
