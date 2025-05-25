### API 명세서
| 기능         | Method | URL                  | request            | response           | HTTP Status |
|--------------|--------|----------------------|--------------------|--------------------|-------------|
| 일정 등록     | POST   | /api/schedules       | JSON body          | 등록된 일정 정보    | 201 Created |
| 전체 일정 조회| GET    | /api/schedules       | query param (선택) | 일정 목록           | 200 OK      |
| 단건 일정 조회| GET    | /api/schedules/{id}  | path param         | 일정 1건 정보       | 200 OK      |
| 일정 수정     | PUT    | /api/schedules/{id}  | JSON body + pwd    | 수정된 일정 정보    | 200 OK      |
| 일정 삭제     | DELETE | /api/schedules/{id}  | JSON body(pwd)     | 없음                | 204 No Content |
