package input;

import java.util.Random;

public class Unifast {

    private int nbTask, nbProc;
    private double U;

    public Unifast(int nbTask, int nbProc, double u) {
        this.nbTask = nbTask;
        this.nbProc = nbProc;
        U = u;
    }

    public double[] generateUtilizations() {
        Random random = new Random(System.currentTimeMillis());
        double[] util = new double[nbTask];
        double nextSumU;
        boolean discard;

        do {
            double sumU = U;
            discard = false;
            for (int i = 0; i < nbTask - 1; i++) {
                nextSumU = sumU * Math.pow(random.nextDouble(), (double) 1 / (nbTask - (i + 1)));
                util[i] = sumU - nextSumU;
                sumU = nextSumU;
                if (util[i] > 1) {
                    discard = true;
                }
            }
            util[nbTask - 1] = sumU;
            if (util[nbTask - 1] > 1) {
                discard = true;
            }
        } while (discard || !utilizationIsValid(util));
        return util;
    }

    /**
     * @param util
     * @return
     */
    private boolean utilizationIsValid(double[] util) {
        double sum = 0;

        for (double u : util) {
            sum += u;
        }
        return sum <= nbProc;
    }
}
