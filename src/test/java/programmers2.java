import static java.util.Arrays.*;


public class programmers2 {

    public static void main(String[] args) {
        int[] goods = {5,3,7};
        int[] boxes = {3,7,6};
        int answer = 0;

        sorting(goods, boxes);
        answer = getAnswer(goods, boxes, answer);

        System.out.println(answer);;
    }

    private static void sorting(int[] goods, int[] boxes) {
        sort(goods);
        sort(boxes);
    }

    private static int getAnswer(int[] goods, int[] boxes, int answer) {
        int boxCount = boxes.length - 1;

        for (int i = goods.length-1; i >= 0; i--) {
            System.out.println("good ====> " + goods[i]);
            for (int j = boxCount; j >= 0; j--) {
                System.out.println(boxes[j] + " : " + goods[i]);
                if (boxes[j] >= goods[i]) {
                    answer++;
                    j = -1;
                    boxCount--;
                }
            }
        }
        return answer;
    }
}
