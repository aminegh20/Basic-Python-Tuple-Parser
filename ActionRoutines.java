/**
 * @author Amine Ghoussaini    
 */

import java.util.ArrayList;
import java.util.HashMap;


public class ActionRoutines {
  /**
   * A static {@code HashMap} that maps each available tuple name to its content
   * that we store in an {@code ArrayList} of Objects.
   */
  static HashMap<String, ArrayList<Object>> tuples = new HashMap<String, ArrayList<Object>>();

  /**
   * A static {@code HashMap} that maps each available non-tuple variable (either a string or an integer in this case)
   * to its value.
   */
  static HashMap<String, Object> variables = new HashMap<String, Object>();

  /**
   * A static {@code ArrayList} of {@code String} useful for unpacking, e.g, (x1, x2, ...) = T 
   * where T is a tuple and x1, x2, ... are variable names.
   * Used to store the names x1, x2,... temporarily in order to retrieve their values from T.
   */
  static ArrayList<String> unpacking = new ArrayList<String>();

  /**
   * Creates an entry in {@code HashMap} tuples called tuple_id.
   * If tuple_id is found in {@code HashMap} variables, it means that the variable with 
   * name tuple_id was previously declared in the  program as either an integer or a string,
   * and hence needs to be removed from {@code HashMap} variables.
   * 
   * @param tuple_id 
   */
  void create_tuple(String tuple_id) {
    tuples.put(tuple_id, new ArrayList<Object>());
    if (variables.containsKey(tuple_id)) {
      variables.remove(tuple_id);
    }
  }

  /**
   * Creates an entry in {@code HashMap} variables called string_id and maps it to value
   * If string_id is found in {@code HashMap} tuples, it means that the variable with name 
   * string_id was previously declared in the program as a tuple, and hence needs to be removed 
   * from {@code HashMap} tuples.
   * 
   * @param string_id the name of the variable
   * @param value the value of the variable
   */
  void create_string(String string_id, String value) {
    variables.put(string_id, value);
    if (tuples.containsKey(string_id) == true) {
      tuples.remove(string_id);
    }
  }

  /**
   * Creates an entry in {@code HashMap} variables called int_id and maps it to value
   * If int_id is found in {@code HashMap} tuples, it means that the variable with name int_id 
   * was previously declared in the  program as a tuple, and hence needs to be removed from 
   * {@code HashMap} tuples.
   * 
   * @param int_id
   * @param value
   */
  void create_int(String int_id, int value) {
    variables.put(int_id, value);
    if (tuples.containsKey(int_id) == true) {
      tuples.remove(int_id);
    }
  }

  /**
   * Adds the specified value to the {@code ArrayList<Object>} that {@code String} tuple_id maps to.
   * 
   * @param tuple_id
   * @param value
   */
  void add_value(String tuple_id, Object value) {
    tuples.get(tuple_id).add(value);
  }

  /**
   * Updates the value at index index of the {@code ArrayList} mapped to by {@code String} tuple_id.
   * If index is negative, indexing is readjusted to be relative to the last element.
   * 
   * @param tuple_id 
   * @param index
   * @param value
   */
  void update_value(String tuple_id, int index, Object value) {
    if (index < 0) {
      index += tuples.get(tuple_id).size();
    }
    tuples.get(tuple_id).set(index, value);
  }

  /**
   * Adds the elements of the {@code ArrayList} mapped to b {@code String} tuple_id_2
   * to the {@code ArrayList} mapped to by {@code String} tuple_id_1
   * 
   * @param tuple_id_1
   * @param tuple_id_2
   */
  void concatenate_tuple(String tuple_id_1, String tuple_id_2) {
    if (tuples.containsKey(tuple_id_2) == true) {
      ArrayList<Object> to_be_added_to = tuples.get(tuple_id_1);
      ArrayList<Object> to_be_added_from = tuples.get(tuple_id_2);
      for (Object object : to_be_added_from) {
        to_be_added_to.add(object);
      }
    } 
  }

  /**
   * Adds the elements of the {@code ArrayList} mapped to by {@code String} tuple_id_2
   * to the {@code ArrayList} mapped to by {@code String tuple_id_1 starting at index
   * begin and ending at index end - 1.
   * If {@code String} begin is {@code null}, the start is considered to be index 0.
   * If {@code String} end is {@code null}, the end is considered to be the last index of the {@code ArrayList}.
   * 
   * @param tuple_id_1 
   * @param tuple_id_2
   * @param begin
   * @param end
   */
  void slice_tuple(String tuple_id_1, String tuple_id_2, String begin, String end) {
    ArrayList<Object> add_to = tuples.get(tuple_id_1);
    ArrayList<Object> add_from = tuples.get(tuple_id_2);
    int st = (begin == null ? 0 : Integer.parseInt(begin));
    int ed = (end == null ? add_from.size() : Integer.parseInt(end));
    for (int i = st; i < ed; ++i) {
      add_to.add(add_from.get(i));
    }
  }

  /**
   * Adds the {@code String} variable_id to the {@code ArrayList} unpacking.
   * This is useful for the method unpack.
   * 
   * @see unpack
   * @param variable_id
   */
  void add_to_pack(String variable_id) {
    unpacking.add(variable_id);
  }

  /**
   * For each {@code String} S in {@code ArrayList} unpacking, assign variable with name S to the
   * corresponding value in the {@code ArrayList} mapped to by tuple_id.
   * 
   * @param tuple_id
   */
  void unpack(String tuple_id) {
    int index = 0;
    for (String variable : unpacking) {
      variables.put(variable, tuples.get(tuple_id).get(index));
      index += 1;
    }
    unpacking.clear();
  }

  /**
   * Prints the content of {@code ArrayList} mapped to by id if id is in {@code HashMap} tuples. 
   * Otherwise, it prints the value mapped to by id in {@code HashMap} variables.
   * In other words, it prints a tuple if {@code String} id is the name of a tuple, a {@code String} 
   * if id is the name of String variable, and {@code Integer} if id is the name of an integer variable.
   * 
   * @param id
   */
  void print(String id) {
    if (tuples.containsKey(id) == true) {
      System.out.print(id + " = (");
      ArrayList<Object> tup = tuples.get(id);
      int n = tup.size();
      for (int i = 0; i < n; ++i) {
        System.out.print(tup.get(i));
        if (i < n - 1) {
          System.out.print(", ");
        }
      }
      System.out.println(')');
    } else if (variables.containsKey(id) == true) {
      System.out.println(id + " = " + variables.get(id));
    }
  }
}
