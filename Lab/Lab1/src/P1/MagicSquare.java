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
//         �ж����������Ƿ�Ϸ��������Ϸ��������ڸ���С�����򷵻�false
         if (n <= 0 || n % 2 == 0)
             return false;
        int magic[][] = new int[n][n];
        int row = 0, col = n / 2, i, j, square = n * n;
        // ����������MagicSquare�����巽���Ǵӵ�һ���м俪ʼ�����Ϸ���������
        // ����ÿ������һ�о�����n-1�У�ÿ������n-1�о���ת����һ�У������巽�򲻱�
        // ÿ��д��n�����ʹӸ�λ�����·���ʼ��д
        for (i = 1; i <= square; i++) {
            magic[row][col] = i; // ��1���ڵ�һ�����м�
            if (i % n == 0)
                row++; // ���������n������˵��һ��λ���Ѿ������룬�������һ��ͬһ��
            else {
                if (row == 0) // ������������ڵ���
                    row = n - 1;
                else
                    row--;
                if (col == (n - 1)) // ���������������������
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