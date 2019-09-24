
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = getN(scanner);
        int W = getW(scanner);

        String[][] answer = init(scanner, N);
        String[][] rotate = new String[N][N];

        rotate = getTurnData(answer, N, W);

        printAnswer(N, answer);

        System.out.println("====================");
        //printAnswer(N, answer);
        printAnswer(N, rotate);
    }

    public static List<List<String>> getSnailTurnList(int N, String[][] data){
        List<List<String>> snailTurnList = new ArrayList<>();
        int f=0;
        while(f<N/2){
            snailTurnList.add(new ArrayList<>());
            for(int i=f; i<N-1-f; i++){
                snailTurnList.get(f).add(data[f][i]);
            }
            for(int i=f; i<N-1-f; i++){
                snailTurnList.get(f).add(data[i][N-1-f]);
            }
            for(int i=N-1-f; i>f; i--){
                snailTurnList.get(f).add(data[N-1-f][i]);
            }
            for(int i=N-1-f; i>f; i--){
                snailTurnList.get(f).add(data[i][f]);
            }
            f++;
        }
        if(N%2==1){
            snailTurnList.add(new ArrayList<>());
            snailTurnList.get(snailTurnList.size()-1).add(data[N/2][N/2]);
        }
        return snailTurnList;
    }

    public static String[][] getTurnData(String[][] data, int N, int W){
        String[][] newData = new String[N][N];
        System.out.println(W);

        int dir = (W>0) ? 1 : -1;

        List<List<String>> snailTurnList = getSnailTurnList(N, data);
        int f = 0;
        while(f<N/2){
            int frame_size = 2*(N - 2*f) + 2*(N-2*f-2);
            int pointer = (dir==-1) ? Math.abs(W)%frame_size : frame_size-Math.abs(W)%frame_size;
            for(int i=f; i<N-1-f; i++){
                if(pointer >= frame_size){
                    pointer = 0;
                }
                newData[f][i] = snailTurnList.get(f).get(pointer++);
            }
            for(int i=f; i<N-1-f; i++){
                if(pointer >= frame_size){
                    pointer = 0;
                }
                newData[i][N-1-f] = snailTurnList.get(f).get(pointer++);
            }
            for(int i=N-1-f; i>f; i--){
                if(pointer >= frame_size){
                    pointer = 0;
                }
                newData[N-1-f][i] = snailTurnList.get(f).get(pointer++);
            }
            for(int i=N-1-f; i>f; i--){
                if(pointer >= frame_size){
                    pointer = 0;
                }
                newData[i][f] = snailTurnList.get(f).get(pointer++);
            }
            f++;
            dir *= -1;
        }
        if(N%2 == 1){
            newData[N/2][N/2] = data[N/2][N/2];
        }
        return newData;
    }

    private static void printAnswer(int n, String[][] answer) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(answer[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static String[][] init(Scanner scanner, int n) {
        String[][] data = new String[n][n];
        scanner.nextLine();
        for(int i=0; i<n; i++){
            String[] str = scanner.nextLine().split(" ");
            for(int j=0; j<str.length; j++){
                data[i][j] = str[j];
            }
        }
        return data;
    }

    private static int getW(Scanner scanner) {
        return scanner.nextInt();
    }

    private static int getN(Scanner scanner) {
        return scanner.nextInt();
    }



}
