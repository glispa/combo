grammar Macro;

/* Parser rules */

// A template is a list of texts and macro chains. It matches the full input text
template : (macroChain | text)* EOF;

// A macro chain is a tag within BEGIN and END containing a list of macros separated by PIPE. SP are optional
macroChain : BEGIN SP* macro (SP* PIPE SP* macro)* SP* END;

// A macro is an identifier and a serie of args separated by SP
macro : macroId (SP+ arg)*;

// A macro identifier is a sequence of ID_CHAR
macroId : ID_CHAR+;

// Arg can be anything but SP
arg: (OTHER_CHAR|ID_CHAR|BEGIN|escSpace|escPipe|escEnd|escEsc)+;

// Escape sequence for the ESC char
escEsc : ESC ESC { ((WritableToken)$start).setText(""); };

// Escape sequence for the SP char
escSpace : ESC SP { ((WritableToken)$start).setText(""); };

// Escape sequence for the PIPE char
escPipe : ESC PIPE { ((WritableToken)$start).setText(""); };

// Escape sequence for the END char
escEnd : ESC END { ((WritableToken)$start).setText(""); };

// Text can basically contain every tokens from the lexer or a begin that is not a macro (no END)
text : (BEGIN (OTHER_CHAR|SP|ESC|ID_CHAR|PIPE)*)
        | (OTHER_CHAR|SP|ESC|ID_CHAR|PIPE|END)+;

/* Lexer rules */

BEGIN : '${';

END : '}';

PIPE : '|';

SP : ' ' | '\t';

ESC : '\\';

ID_CHAR : [a-zA-Z0-9_.];

OTHER_CHAR : ~([a-zA-Z0-9_.]);