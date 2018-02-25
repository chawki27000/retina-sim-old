package architecture;

import java.util.ArrayList;

public class NoC {

    Router[][] routerMatrix;


    public NoC(int SquareSize, int NBVC, int VCSIZE) {
        for (int i = 0; i < SquareSize; i++) {
            for (int j = 0; j < SquareSize; j++) {

                InPort ileft = new InPort(0, NBVC, VCSIZE);
                InPort iright = new InPort(1, NBVC, VCSIZE);
                InPort iup = new InPort(2, NBVC, VCSIZE);
                InPort idown = new InPort(3, NBVC, VCSIZE);


            }
        }
    }

}
