package architecture;


public class Router {

    public Router(int x, int y, InPort inLeft, InPort inRight, InPort inUp,
                  InPort inDown, OutPort oLeft, OutPort oRight, OutPort oUp,
                  OutPort oDown) {
        this.x = x;
        this.y = y;
        InLeft = inLeft;
        InRight = inRight;
        InUp = inUp;
        InDown = inDown;
        OLeft = oLeft;
        ORight = oRight;
        OUp = oUp;
        ODown = oDown;
    }

    int x, y;
    InPort InLeft, InRight, InUp, InDown;
    OutPort OLeft, ORight, OUp, ODown;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public InPort getInLeft() {
        return InLeft;
    }

    public void setInLeft(InPort inLeft) {
        InLeft = inLeft;
    }

    public InPort getInRight() {
        return InRight;
    }

    public void setInRight(InPort inRight) {
        InRight = inRight;
    }

    public InPort getInUp() {
        return InUp;
    }

    public void setInUp(InPort inUp) {
        InUp = inUp;
    }

    public InPort getInDown() {
        return InDown;
    }

    public void setInDown(InPort inDown) {
        InDown = inDown;
    }

    public OutPort getOLeft() {
        return OLeft;
    }

    public void setOLeft(OutPort oLeft) {
        OLeft = oLeft;
    }

    public OutPort getORight() {
        return ORight;
    }

    public void setORight(OutPort oRight) {
        ORight = oRight;
    }

    public OutPort getOUp() {
        return OUp;
    }

    public void setOUp(OutPort oUp) {
        OUp = oUp;
    }

    public OutPort getODown() {
        return ODown;
    }

    public void setODown(OutPort oDown) {
        ODown = oDown;
    }

}
