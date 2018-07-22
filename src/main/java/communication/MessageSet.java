package communication;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageSet {

	
	private ArrayList<Message> m_list;
	public ArrayList<Message> getM_list() {
		return m_list;
	}
	public void setM_list(ArrayList<Message> m_list) {
		this.m_list = m_list;
	}
	public MessageSet() {
		m_list = new ArrayList<Message>();
	}
	public void addMessage(Message m)
	{
		m_list.add(m);
	}

	
    private int gcd(int x, int y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public int gcd(int... numbers) {
        return Arrays.stream(numbers).reduce(0, (x, y) -> gcd(x, y));
    }
    
    
    public  int getHyperPeriod() {
    	int[]  periods = new int[m_list.size()];
    	for (Message m: m_list) periods[m.getId()] = m.getPeriod();
        return Arrays.stream(periods).reduce(1, (x, y) -> x * (y / gcd(x, y)));
    }
    
}
