package interfaces;

import SearchFilters.SearchFilter;

public interface Filtrable {
	
	public boolean isFiltred();
	
	public void Filtre(SearchFilter filter);
	
	public void unFiltre(SearchFilter filter);
	
	public SearchFilter getFilter();
	
}
