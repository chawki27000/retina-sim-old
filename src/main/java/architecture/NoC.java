package architecture;

import java.util.ArrayList;

import communication.coordinates;

/**
 * This class aims to create and instantiate
 * a 2D NoC with all its components
 */
public class NoC {

    // TODO : work on NoC initialisation
    public static Router[][] routerMatrix;
    int squareSize;

    /**
     * NoC class constructor
     *
     * @param squareSize Mesh 2D X and Y axes size
     * @param NBVC       VC number per port
     * @param VCSIZE     size of VC buffer
     */
    public NoC(String name, int squareSize, int NBVC, int VCSIZE) {

        this.squareSize = squareSize;
        routerMatrix = new Router[squareSize][squareSize];

        // Routers instantiation
        int count = 1;
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                routerMatrix[i][j] = routerInitialisation(count, NBVC, VCSIZE, i, j);
                count++;
            }
        }

        // NoC Building
        routerLink();
    }

    /**
     * Router port and position initialisation
     *
     * @param NBVC   VC number per port
     * @param VCSIZE size of VC buffer
     * @param x      Router position in X axe
     * @param y      Router position in Y axe
     * @return Router object
     */
    private Router routerInitialisation(int idx, int NBVC, int VCSIZE, int x, int y) {

        // Initialise Input ports
        InPort inUp = new InPort(0, NBVC, VCSIZE);
        InPort inRight = new InPort(1, NBVC, VCSIZE);
        InPort inDown = new InPort(2, NBVC, VCSIZE);
        InPort inLeft = new InPort(3, NBVC, VCSIZE);

        // Local Inport with a specific parameters
        InPort inLocal = new InPort(4, 100, 40);

        // Initialise Output ports
        OutPort oUp = new OutPort(0);
        OutPort oRight = new OutPort(1);
        OutPort oDown = new OutPort(2);
        OutPort oLeft = new OutPort(3);

        PE pe = new PE();


        return new Router(idx, x + " " + y, x, y,
                inLeft, inRight, inUp, inDown, inLocal,
                oLeft, oRight, oUp, oDown, pe);
    }

    /**
     * This function links the routers between them
     * according to Mesh 2D topology
     */
    private void routerLink() {

        // construct temporary arrayList
        ArrayList<Router> temporaryList = new ArrayList<>();
        Router temporaryRouter = null;

        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                temporaryList.add(routerMatrix[i][j]);
            }
        }

        // Router linking
        for (int i = 0; i < temporaryList.size(); i++) {

            // Near router IDs
            int id = i;
            int idUp = id - squareSize;
            int idRight = id + 1;
            int idLeft = id - 1;
            int idDown = id + squareSize;

            // Link
            if (idUp >= 0) {
                temporaryRouter = temporaryList.get(idUp);
                temporaryList.get(i).getoUp().setDest(temporaryRouter);
            } else {
                temporaryList.get(i).getoUp().setDest(null);
            }

            if (idDown < squareSize * squareSize) {
                temporaryRouter = temporaryList.get(idDown);
                temporaryList.get(i).getoDown().setDest(temporaryRouter);
            } else {
                temporaryList.get(i).getoDown().setDest(null);
            }

            if (idRight % squareSize != 0) {
                temporaryRouter = temporaryList.get(idRight);
                temporaryList.get(i).getoRight().setDest(temporaryRouter);
            } else {
                temporaryList.get(i).getoRight().setDest(null);
            }

            if (id % squareSize != 0) {
                temporaryRouter = temporaryList.get(idLeft);
                temporaryList.get(i).getoLeft().setDest(temporaryRouter);
            } else {
                temporaryList.get(i).getoLeft().setDest(null);
            }

        }

    }

    public Router getRouter(coordinates crd) {
        return routerMatrix[crd.getX()][crd.getY()];
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                str += routerMatrix[i][j].toString() + "\n";
            }
        }

        return str;
    }
}