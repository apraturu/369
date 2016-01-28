import java.util.ArrayList;
public class MonitorTotals {
	public String recordType;
	public ArrayList<Long> msgTotals;
	public ArrayList<Long> userTotals;
	public ArrayList<Long> newMsgTotals;

	public MonitorTotals(String recordType, ArrayList<Long> msgTotals, ArrayList<Long> userTotals, ArrayList<Long> newMsgTotals) {
		this.recordType = recordType;
		this.msgTotals = msgTotals;
		this.userTotals = userTotals;
		this.newMsgTotals = newMsgTotals;
	}

	public void updateMonitorTotals(long msgTotal, long userTotal, long newMsgTotal) {
		msgTotals.add(msgTotal);
		userTotals.add(userTotal);
		newMsgTotals.add(newMsgTotal);
	}
}
