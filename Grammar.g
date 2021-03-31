grammar Grammar;

@members { 
  ActionRoutines actions = new ActionRoutines();
}

prog : (stmt)+
        ;

stmt  : assignment 
        | unpack 
        | print
        ;

assignment  : ID '=' { actions.create_tuple($ID.text); } assignment_tail[$ID.text]
                | ID '[' INT ']' '=' value { actions.update_value($ID.text, Integer.parseInt($INT.text), $value.val); }
                | ID '=' STRING { actions.create_string($ID.text, $STRING.text); }
                | ID '=' INT    { actions.create_int($ID.text, Integer.parseInt($INT.text)); }
                ;

assignment_tail [String tuple_id] :                                                     
                '(' value_tuple [$tuple_id] ')' 
                | id1 = ID { actions.concatenate_tuple($tuple_id, $id1.text); } ( '+' id2 = ID {actions.concatenate_tuple($tuple_id, $id2.text); } )*
                | ID '[' (begin = INT)? ':' (end = INT)? ']' { actions.slice_tuple($tuple_id, $ID.text, $begin.text, $end.text); }
                ;

value_tuple [String tuple_id]: 
             t1 = value { actions.add_value($tuple_id, $t1.val); } (',' t2 = value { actions.add_value($tuple_id, $t2.val); } )*
             |
             ;

value returns[Object val]: 
            STRING { $val = new String($STRING.text); }
          | INT    { $val = Integer.parseInt($INT.text); }
          | ID     { $val = new String($ID.text); }
          ;

unpack : '(' t3 = ID { actions.add_to_pack($t3.text); } (',' t4 = ID { actions.add_to_pack($t4.text); } )* ')' '=' t5 = ID { actions.unpack($t5.text); }
       ;

print : 'print' '(' STRING ')' { System.out.println($STRING.text); }
      | 'print' '(' INT ')'     { System.out.println($INT.text); }
      | 'print' '(' ID ')' { actions.print($ID.text); }
      ;

//represents strings
STRING : '\'' ('a'..'z' | 'A'..'Z' | '0'..'9' | ' ')* '\'' ;

//represents positive or negative integers
INT : ('-')? ('0'..'9')+ ;

//represents an ID
ID : ('a'..'z' | 'A'..'Z') ('a'..'z' | 'A'..'Z' | '0'..'9')* ;

//consume whitespace
WS : (' ' | '\t' | '\n' | '\r')+ { skip(); } ;

//consume comments
COMMENTS : ('//') (~('\n' | '\r'))* { skip(); }	;