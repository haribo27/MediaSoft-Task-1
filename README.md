# Тестовое задание №1 на Java SE + JUnit

## Автор: Зубцов Алексей

### Описание проекта

Данное приложение предназначено для обработки информации о рейсах, представленной в формате JSON, с помощью регулярных выражений. Приложение парсит данные о рейсах, экипаже и времени полетов, а также рассчитывает количество часов налета экипажа по месяцам с учетом специальных ограничений. Результаты сохраняются в выходной файл с отметками о превышении допустимых часов за месяц, неделю и день.

### Основные функции:
- **Парсинг входного файла** с информацией о рейсах и экипаже с помощью регулярных выражений.
- **Подсчет часов налета экипажа** по месяцам.
- **Проверка на превышение допустимых часов налета** за день, неделю и месяц.
- **Запись результатов** с отметками о превышениях в выходной файл.

### Входные данные:
- **Файл `flights.json`** — содержит информацию о рейсах и экипаже в формате JSON.
### Данные на выходе приложения:
- **Файл `flightsReport`** - содержит отчет о налетных часах экипажа в формате JSON.

Пример структуры JSON:
```json
{
  "planeType": "Boeing 737",
  "flightNumber": "SU123",
  "departureTime": "2024-01-01T10:00",
  "arrivalTime": "2024-01-01T12:00",
  "departureAirport": "SVO",
  "arrivalAirport": "JFK",
  "crew": [
    {
      "memberId": "1",
      "name": "John Doe",
      "role": "Captain"
    },
    {
      "memberId": "2",
      "name": "Jane Smith",
      "role": "First Officer"
    }
  ]
}
```

