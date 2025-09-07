package utils;

public class SQLErrorsValidator {

    public static void validate(String SQLState) {
        String classOfErrors = SQLState.substring(0, 2);
        switch (classOfErrors) {
            case "01":
                System.out.println("Предупреждение");
                break;
            case "02":
                System.out.println("Нет данных (это также предупреждение по стандарту SQL)");
                break;
            case "03":
                System.out.println("Запрос SQL ещё не завершён");
                break;
            case "08":
                System.out.println("Ошибка соединения");
                break;
            case "09":
                System.out.println("Исключение триггерного действия");
                break;
            case "0A":
                System.out.println("Функционал не поддерживается");
                break;
            case "0B":
                System.out.println("Недопустимая инициализация транзакции");
                break;
            case "0F":
                System.out.println("Исключение локатора");
                break;
            case "0L":
                System.out.println("Недопустимый владелец привилегии");
                break;
            case "0P":
                System.out.println("Неверная спецификация роли");
                break;
            case "0Z":
                System.out.println("Исключение диагностики");
                break;
            case "20":
                System.out.println("Не найден случай");
                break;
            case "21":
                System.out.println("Нарушение количества элементов");
                break;
            case "22":
                System.out.println("Исключение данных");
                break;
            case "23":
                System.out.println("Нарушение целостности ограничений");
                break;
            case "24":
                System.out.println("Некорректное состояние курсора");
                break;
            case "25":
                System.out.println("Некорректное состояние транзакции");
                break;
            case "26":
                System.out.println("Некорректное имя SQL-запроса");
                break;
            case "27":
                System.out.println("Нарушение изменений данных триггером");
                break;
            case "28":
                System.out.println("Некорректная спецификация авторизации");
                break;
            case "2B":
                System.out.println("Привилегированные дескрипторы зависимых прав всё ещё существуют");
                break;
            case "2D":
                System.out.println("Некорректное завершение транзакции");
                break;
            case "2F":
                System.out.println("Исключение процедуры SQL");
                break;
            case "34":
                System.out.println("Некорректное имя курсора");
                break;
            case "38":
                System.out.println("Исключение внешней процедуры");
                break;
            case "39":
                System.out.println("Исключение вызова внешней процедуры");
                break;
            case "3B":
                System.out.println("Исключение точки сохранения");
                break;
            case "3D":
                System.out.println("Некорректное имя каталога");
                break;
            case "3F":
                System.out.println("Некорректное имя схемы");
                break;
            case "40":
                System.out.println("Откат транзакции");
                break;
            case "42":
                System.out.println("Ошибка синтаксиса или нарушение правила доступа");
                break;
            case "44":
                System.out.println("Нарушение опции WITH CHECK");
                break;
            case "53":
                System.out.println("Недостаточность ресурсов");
                break;
            case "54":
                System.out.println("Превышение пределов программы");
                break;
            case "55":
                System.out.println("Объект не находится в требуемом состоянии");
                break;
            case "57":
                System.out.println("Интервенция оператора");
                break;
            case "58":
                System.out.println("Система дала сбой (ошибки вне PostgreSQL)");
                break;
            case "F0":
                System.out.println("Ошибка файла конфигурации");
                break;
            case "HV":
                System.out.println("Ошибка внешнего модуля данных (SQL/MED)");
                break;
            case "P0":
                System.out.println("Ошибка PL/pgSQL");
                break;
            case "XX":
                System.out.println("Внутренняя ошибка");
                break;
            default:
                System.out.println("Передано неверное значение");
        }
    }

}
