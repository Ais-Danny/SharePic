package top.aisdanny.pojo;

public class Page {
	int startPage;
	int pageSize;
	int total;

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Page(int startPage, int pageSize, int total) {
		super();
		this.startPage = startPage;
		this.pageSize = pageSize;
		this.total = total;
	}

	public Page() {
		super();
	}
}
