## Веб-приложение "TODO List"

Приложение представляет собой сервис для составления списка задач.

Стек технологий: 
- Spring boot
- Thymeleaf
- Bootstrap
- Hibernate
- PostgreSql

Функциональные возможности приложения:
 - Страница входа:
    - Вход
    - Переход к регистрации
 
![login_page](src/main/resources/screenshots/login.png)

 - Главная страница:
    - Список добавленных задач.
    - Фильтрация задач - отображение всех задач, только выполенных, и только новых.
    - Кнопка для добавления новых задач.

![main page](src/main/resources/screenshots/tasks.png)
 
   - Страница добавления задачи:
        - Выбор одной или нескольких категорий.

![add task](src/main/resources/screenshots/add_task.png)

   - Страница с описанием задачи:
      - Кнопка для пометки задачи выполненной.
      - Кнопка редактирования задачи.
      - Кнопка удаления задачи.

![task info](src/main/resources/screenshots/task_info.png)

   - Страница редактирования задачи:

![edit task](src/main/resources/screenshots/edit_task.png)