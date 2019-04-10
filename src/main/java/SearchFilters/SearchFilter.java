package SearchFilters;

import interfaces.Visitor;
import interfaces.Filtrable;

public abstract class SearchFilter implements Visitor {
	
	Filtrable filtred;
	
	public SearchFilter(Filtrable filtred) {
		this.filtred = filtred;
	}

	public Filtrable getFiltred() {
		return filtred;
	}

	public void setFiltred(Filtrable filtred) {
		this.filtred = filtred;
	}
	
	public void cleanFilter() {
		filtred.unFiltre(this);
	}
		
}
