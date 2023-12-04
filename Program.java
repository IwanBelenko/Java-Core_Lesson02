import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    private static  char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;


    public static void main(String[] args) {
        while (true){
            initialize();
            printField();
            while (true){
                humanTurn();
                printField();
                if (checkGameState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkGameState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    static void initialize(){
        fieldSizeY = 5;
        fieldSizeX = 5;

        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++){
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int y = 0; y < fieldSizeY; y++){
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++){
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
    static void humanTurn(){
        int x;
        int y;

        do {
            System.out.print("Введите координаты хода X и Y (от 1 до 3)\nчерез пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));

        field[y][x] = DOT_HUMAN;
    }

    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn(){
        int x;
        int y;

        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));

        field[y][x] = DOT_AI;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return field[y][x] == DOT_EMPTY;
    }

    /**
     * Проверка доступности ячейки игрового поля
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x< fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /**
     * Метод проверки состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return результат проверки состояния игры
     */
    static boolean checkGameState(char dot, String s){
        if (checkWin(dot)){
            System.out.println(s);
            return true;
        }
        if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int y = 0; y < fieldSizeY; y++){
            for (int x = 0; x < fieldSizeX; x++){
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    /**
     * Проверка победы игрока
     * @param dot фишка игрока
     * @return признак победы

    static boolean checkWin(char dot){
        // Проверка по трем горизонталям
        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;

        // Проверка по трем вертикалям
        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;

        // Проверка по диагоналям
        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;

        return false;
    }*/

    static boolean checkWin(char dot) {
        int winCount = 4; // Количество фишек для победы

        // Проверка горизонталей и вертикалей
        for (int i = 0; i < fieldSizeY; i++) {
            if (checkLine(dot, i, 0, 0, 1, winCount)) return true; // Проверка горизонтальной линии
            if (checkLine(dot, 0, i, 1, 0, winCount)) return true; // Проверка вертикальной линии
        }

        // Проверка диагоналей
        if (checkLine(dot, 0, 0, 1, 1, winCount)) return true; // Проверка главной диагонали
        if (checkLine(dot, 0, fieldSizeY - 1, 1, -1, winCount)) return true; // Проверка побочной диагонали

        return false;
    }

    /**
     * Проверка линии на наличие победной комбинации фишек
     * @param dot фишка игрока
     * @param startY начальная координата по оси Y
     * @param startX начальная координата по оси X
     * @param deltaY шаг по оси Y
     * @param deltaX шаг по оси X
     * @param winCount количество фишек для победы
     * @return признак победы
     */
    static boolean checkLine(char dot, int startY, int startX, int deltaY, int deltaX, int winCount) {
        for (int i = 0; i < winCount; i++) {
            int y = startY + i * deltaY;
            int x = startX + i * deltaX;
            if (!isCellValid(x, y) || field[y][x] != dot) {
                return false; // Фишки не образуют победную комбинацию
            }
        }
        return true; // Победная комбинация найдена
    }

}
