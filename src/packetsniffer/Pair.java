package packetsniffer;


public class Pair<T1, T2> implements NameValuePair<T1, T2> {

	private T1 name;
	private T2 value;


	public Pair(T1 name, T2 value) {
		this.name = name;
		this.value = value;
	}


	@Override
	public T1 getName() {
		return name;
	}


	@Override
	public T2 getValue() {
		return value;
	}


	@Override
	public String toString() {
		return String.format("\nName: %s\nValue: %s\n", name, value);
	}
}
