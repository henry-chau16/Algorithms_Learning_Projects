import java.lang.reflect.Field;
import java.util.Date;

public class Person {

	private String lastName;
	private String firstName;
	private Date dateOfBirth;

	public Person(String ln, String fn, Date dob) {
		this.lastName = ln;
		this.firstName = fn;
		this.dateOfBirth = dob;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	
	
	/**
	 * Do not change this code.
	 * This method creates for each person a long string that combines all its data.
	 * 
	 * You can use this method as is for debugging your code.
	 * 
	 * Taken from:
	 * http://stackoverflow.com/questions/1526826/printing-all-variables-value-from-a-class
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		String separator = ", ";

		result.append("{");

		Field[] fields = this.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				result.append(field.getName());
				result.append(": ");
				result.append(field.get(this));
			} catch (IllegalAccessException ex) {
				System.out.println(ex);
			}
			result.append(separator);
		}
		result.append("}");

		return result.toString();
	}
}
