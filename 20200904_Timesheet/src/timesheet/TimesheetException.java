package timesheet;

@SuppressWarnings("serial")
public class TimesheetException extends Exception {

	public TimesheetException(String reason) {
		super(reason);
	}

	public TimesheetException() {
		super();
	}

}
