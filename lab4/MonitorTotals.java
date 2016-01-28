public class MonitorTotals {
	public String recordType;
	public ArrayList<Integer> msgTotals;
	public ArrayList<Integer> userTotals;
	public ArrayList<Integer> newMsgTotals;

	public MonitorTotals(String recordType, ArrayList<Integer> msgTotals, ArrayList<Integer> userTotals, ArrayList<Integer> newMsgTotals) {
		this.recordType = recordType;
		this.msgTotals = msgTotals;
		this.userTotals = userTotals;
		this.newMsgTotals = newMsgTotals;
	}

	public void updateMonitorTotals(int msgTotal, int userTotal, int newMsgTotal) {
		msgTotals.add(msgTotal);
		userTotals.add(userTotal);
		newMsgTotals.add(newMsgTotal);
	}
}