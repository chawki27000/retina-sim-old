package luncher;

import architecture.NoC;

public class Main {


    public static void main(String[] args) {
        NoC noc = new NoC(3, 8, 12);

        int[] src = new int[]{0,0};
        int[] dest = new int[]{2,2};

        noc.sendMesssage(src, dest, 2048);
    }
}
