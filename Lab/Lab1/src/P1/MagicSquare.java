package P1;

import java.io.*;
import java.util.*;

public class MagicSquare {
    static final int N = 300;

    public static boolean isLegalMagicSquare (String fileName) {
        int row = 0, col = 0;
        int i = 0, j = 0;
        String line = null;
        int[][] square = new int[N][N];
        File file = new File(fileName);

        // compute number of row
        try {
            BufferedReader br_row = new BufferedReader(new FileReader(file));
            while ((line = br_row.readLine()) != null)
                row += 1;
            br_row.close();
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            return false;
        }

        // compute number of column and transfer string to int
        try {
            BufferedReader br_col = new BufferedReader(new FileReader(file));
            while ((line = br_col.readLine()) != null) {
                String[] l = line.split("\t");
                col = l.length;
                // row != column
                if (col != row) {
                    System.out.println("NOT MATRIX!");
                    br_col.close();
                    return false;
                }
                for (j = 0; j < row; j++) {
                    // negative numbers or decimals
                    if (l[j].contains(".") || l[j].contains("-")) {
                        System.out.println("ILLEGAL NUMBER!");
                        br_col.close();
                        return false;
                    }
                    square[i][j] = Integer.parseInt(l[j]);
                }
                i += 1;
            }
            br_col.close();
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            return false;
        }

        // check equality of the sum in all rows, columns and diagonals
        int firstsum = 0, rowsum = 0, colsum = 0;
        int diagsum[] = new int[2];
        for (i = 0; i < row; i++)
            firstsum += square[0][i];
        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                rowsum += square[i][j]; // compute sum in each row
                colsum += square[j][i]; // compute sum in each column
            }
            if (rowsum != firstsum || colsum != firstsum)
                return false;
            rowsum = 0;
            colsum = 0;
            // compute sums of both diagonals
            diagsum[0] += square[i][i];
            diagsum[1] += square[i][row - i - 1];
        }
        if (diagsum[0] != firstsum || diagsum[1] != firstsum)
            return false;

        System.out.println("MagicSquare!");
        return true;
    }

    public static boolean generateMagicSquare(int n) throws IOException {
//         判断输入数据是否合法，若不合法，即存在负数小数，则返回false
         if (n <= 0 || n % 2 == 0)
             return false;
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        // 构造奇数阶MagicSquare，总体方向是从第一行中间开始向右上方依次填数
        // 并且每遇到第一行就跳到n-1行，每遇到第n-1列就跳转到第一列，但总体方向不变
        // 每填写完n个数就从该位置正下方开始填写
        for (i = 1; i <= square; i++) {
            magic[row][col] = i; // 把1放在第一行正中间
            if (i % n == 0)
                row++; // 超出输入的n，或者说下一个位置已经有填入，则放在下一行同一列
            else {
                if (row == 0) // 超出顶行则放在底行
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1)) // 超出最右列则放在最左列
                    col = 0;
                else
                    col++;
            }
        }
        File file = new File("src/P1/txt/6.txt");
        PrintWriter out = new PrintWriter(file);
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                out.print(magic[i][j] + "\t");
            out.println();
        }
        out.close();
        return true;
    }

    public static void main(String[] args) throws IOException {
        boolean temp;
        for (char i = '1'; i <= '5'; i++) {
            System.out.print(i + ".");
            temp = isLegalMagicSquare("src/P1/txt/" + i + ".txt");
            System.out.println(String.valueOf(temp));
        }
        System.out.print("Input a Number: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (generateMagicSquare(n) == false)
            System.out.println("6.Wrong Input");
        else {
            System.out.print("6" + ".");
            temp = isLegalMagicSquare("src/P1/txt/6.txt");
            System.out.println(String.valueOf(temp));
        }
        sc.close();
        return;
    }
}