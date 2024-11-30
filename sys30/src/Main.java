import java.util.Scanner;

class ChessboardPerimeterUsingArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();  // количество выпиленных клеток
        boolean[][] board = new boolean[8][8]; // шахматная доска восем на восем

        // чтение координат выпиленных клеток
        for (int i = 0; i < N; i++) {
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            board[row][col] = true;
        }

        int perimeter = calculatePerimeter(board);
        System.out.println(perimeter);
    }

    private static int calculatePerimeter(boolean[][] board) {
        int perimeter = 0;

        // Пара массивов для смещения по соседним клеткам
        int[] dRow = {1, 0, -1, 0}; // по строкам
        int[] dCol = {0, 1, 0, -1}; // по столбцам


        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // Если клетка выпилена
                if (board[row][col]) {
                    // по каждому направлению
                    for (int i = 0; i < 4; i++) {
                        int sosedRow = row + dRow[i];
                        int sosedCol = col + dCol[i];

                        // не вышли ли мы за границы доски и выпилена ли соседняя клетка
                        if (sosedRow < 0 || sosedRow >= 8 || sosedCol < 0 || sosedCol >= 8 || !board[sosedRow][sosedCol]) {
                            perimeter++; // клетка за границей или не выпилена, увеличиваем периметр
                        }
                    }
                }
            }
        }

        return perimeter;
    }
}
