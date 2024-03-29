package input;

public class Task {

    int src_x, src_y, dest_x, dest_y;
    int t, c, d;
    int msg_size;

    public Task(int src_x, int src_y, int dest_x, int dest_y, int t, int c, int d, int msg_size) {
        this.src_x = src_x;
        this.src_y = src_y;
        this.dest_x = dest_x;
        this.dest_y = dest_y;
        this.t = t;
        this.c = c;
        this.d = d;
        this.msg_size = msg_size;
    }

    public int getSrc_x() {
        return src_x;
    }

    public int getSrc_y() {
        return src_y;
    }

    public int getDest_x() {
        return dest_x;
    }

    public int getDest_y() {
        return dest_y;
    }

    public int getT() {
        return t;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public int getMsg_size() {
        return msg_size;
    }

    @Override
    public String toString() {
        return "src (" + src_x + "," + src_y + ") ==> dest(" + dest_x + "," + dest_y + ") size : " + msg_size +
                " task (t : " + t + ", c : " + c + ", d : " + d + ")";
    }
}
