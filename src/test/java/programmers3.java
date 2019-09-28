public class programmers3 {

    public static void main(String[] args) {
        int[] sticker2 = {12, 12, 12, 12, 12};
        int[] sticker = {12, 80, 14, 22, 100};
        int answer = 0;

        int temp = 0;
        int temp2 = 0;
        int [] save = new int[sticker.length];

        for (int i = 1; i < sticker.length-1; i++) {
            temp = sticker[i-1];
            temp2 = sticker[i+1];
            if (temp == sticker[i] && sticker[i] == temp2) {
                save[i-1] = temp; save[i+1] = temp2;
            }

            if (temp == sticker[i]) {
                save[i-1] = temp;
                i++;
            } else if (temp < sticker[i]) {
              temp = sticker[i+1];
              if (sticker[i] > temp) {
                  save[i] = sticker[i];
              } else {
                  save[i+1] = temp;
              }
            }
        }

        for (int s : save) {
            System.out.print(s + " ");
            answer += s;
        }
        System.out.println();
        System.out.println(answer);
    }
}
