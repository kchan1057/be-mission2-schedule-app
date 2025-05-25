### API 명세서
| 기능             | Method | URL                         | Request                                | Response           | HTTP Status |
|------------------|--------|-----------------------------|----------------------------------------|--------------------|-------------|
| 일정 등록         | POST   | /api/schedules              | `{ "todo": "...", "writer": "...", "password": "..." }` | 생성된 일정 정보  | 201 Created |
| 전체 일정 조회    | GET    | /api/schedules              | `?writer={writer}&modifiedAt={ISO_DATE}` (선택) | 일정 리스트       | 200 OK      |
| 단건 일정 조회    | GET    | /api/schedules/{id}         | Path Param (`id`)                      | 일정 1건 정보       | 200 OK      |
| 일정 내용 수정     | PATCH  | /api/schedules/{id}/todo    | `{ "todo": "...", "password": "..." }` | 수정된 일정 정보    | 200 OK      |
| 작성자 변경       | PATCH  | /api/schedules/{id}/writer  | `{ "writer": "...", "password": "..." }` | 수정된 일정 정보 | 200 OK      |
| 일정 삭제         | DELETE | /api/schedules/{id}         | `{ "password": "..." }`                | 없음               | 200 OK      |

### ERD (Entity Relationship Diagram)
<pre>schedule
├── id (PK) : BIGINT
├── todo : VARCHAR(255)
├── writer : VARCHAR(100)
├── password : VARCHAR(255)
├── created_at : DATETIME
└── modified_at : DATETIME</pre>
