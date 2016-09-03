package packetsniffer;


public interface NameValuePair<T1, T2> {

	public T1 getName();


	public T2 getValue();


	@Override
	public String toString();
}
