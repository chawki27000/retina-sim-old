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
}
