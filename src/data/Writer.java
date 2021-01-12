package data;

import org.json.simple.JSONArray;

public interface Writer {
    /**
     * muta rezultatele programului intr-un JSONArray
     * cu scopul de a fi printat
     * @param args fisierul unde are loc printarea
     * @return Array-ul JSON ce va fi printat
     */
    JSONArray writeJSON(String args);
}
