import java.util.*;

public class Menu {
    private Scanner sc;
    private Validator validator;

    // Для истории расчётов мы выбрали ArrayList, потому что:
    // - Мы добавляем новые записи только в конец списка.
    // - Доступ к элементам происходит по порядку (просмотр истории).
    // LinkedList тут не подойдёт, потому что он медленнее для доступа по индексу.
    private List<Box<Shape>> history = new ArrayList<>();

    // Для хранения уникальных фигур мы используем HashSet, потому что:
    // - Он автоматически убирает дублирующие фигуры.
    // - Проверка и добавление в HashSet работают быстро.
    private Set<Shape> uniqueShapes = new HashSet<>();

    // Для поиска фигуры по имени мы используем HashMap, потому что:
    // - Это позволяет быстро найти фигуру по её имени.
    // - Доступ к объекту в HashMap очень быстрый.
    private Map<String, Shape> shapesByName = new HashMap<>();

    public Menu(Scanner scanner) {
        this.sc = scanner;
        this.validator = new Validator(scanner);
    }

    public void showMainMenu() {
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int choice = validator.getValidChoice(1, 7);

            switch (choice) {
                case 1 -> {
                    printShapesMenu();
                    int shapeMenuChoice = validator.getValidChoice(1, 2);
                    switch (shapeMenuChoice) {
                        case 1 -> performRectangleCalculation();
                        case 2 -> performCircleCalculation();
                    }
                }
                case 2 -> printProgramInfo();
                case 3 -> printDeveloperInfo();
                case 4 -> showShapesHistory();
                case 5 -> showUniqueShapes();
                case 6 -> findShapeByName();
                case 7 -> {
                    exit = true;
                    System.out.println("Выход из программы...");
                }
                default -> System.out.println("Неверный выбор. Пожалуйста, выберите один из пунктов меню.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n======= Главное меню =======");
        System.out.println("1. Выполнить расчет для фигуры");
        System.out.println("2. Информация о программе");
        System.out.println("3. Информация о разработчике");
        System.out.println("4. Показать историю расчётов");
        System.out.println("5. Показать уникальные фигуры");
        System.out.println("6. Найти фигуру по имени");
        System.out.println("7. Выход");
        System.out.print("Выберите пункт меню: ");
    }

    private void printShapesMenu() {
        System.out.println("\n======= Выбор фигуры =======");
        System.out.println("1. Площадь прямоугольника");
        System.out.println("2. Площадь круга");
        System.out.print("Выберите пункт меню: ");
    }

    private void performRectangleCalculation() {
        try {
            double length = validator.getPositiveDouble("Введите длину прямоугольника (положительное число): ");
            double width = validator.getPositiveDouble("Введите ширину прямоугольника (положительное число): ");
            Rectangle rectangle = new Rectangle(length, width);
            history.add(new Box<>(rectangle)); // Сохраняем фигуру в историю.
            uniqueShapes.add(rectangle); // Добавляем в список уникальных фигур.
            shapesByName.put(rectangle.getName(), rectangle); // Сохраняем фигуру по её имени.
            System.out.println("Результат: " + rectangle.calculateArea());
        } catch (InvalidShapeParameterException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void performCircleCalculation() {
        try {
            double radius = validator.getPositiveDouble("Введите радиус круга (положительное число): ");
            Circle circle = new Circle(radius);
            history.add(new Box<>(circle)); // Сохраняем фигуру в историю.
            uniqueShapes.add(circle); // Добавляем в список уникальных фигур.
            shapesByName.put(circle.getName(), circle); // Сохраняем фигуру по её имени.
            System.out.println("Результат: " + circle.calculateArea());
        } catch (InvalidShapeParameterException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void printProgramInfo() {
        System.out.println("\nИнформация о программе:");
        System.out.println("Эта программа предназначена для работы с различными геометрическими фигурами и расчета их площади.");
    }

    private void printDeveloperInfo() {
        System.out.println("\nИнформация о разработчике:");
        System.out.println("Разработчик: Бехруз, студент магистратуры по направлению 'Разработка и управление в программных проектах'.");
    }

    private void showShapesHistory() {
        System.out.println("\n======= История расчетов =======");
        if (history.isEmpty()) {
            System.out.println("История пуста.");
        } else {
            for (Box<Shape> box : history) {
                Shape shape = box.getContent();
                System.out.println(shape.toString());
                System.out.println("Результат: " + shape.calculateArea());
                System.out.println();
            }
        }
    }

    private void showUniqueShapes() {
        System.out.println("\n======= Уникальные фигуры =======");
        if (uniqueShapes.isEmpty()) {
            System.out.println("Список уникальных фигур пуст.");
        } else {
            for (Shape shape : uniqueShapes) {
                System.out.println(shape);
            }
        }
    }

    private void findShapeByName() {
        System.out.print("Введите имя фигуры для поиска: ");
        String name = sc.next();
        Shape shape = shapesByName.get(name);
        if (shape != null) {
            System.out.println("Найдена фигура: " + shape);
        } else {
            System.out.println("Фигура с таким именем не найдена.");
        }
    }
}
