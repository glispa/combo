# COMBO

## Description

### Motivation

COMBO aims to offer powerful string substitutions without the complexity of a fully-fledged templating engine.

Let's say we want to dynamically build URLs.

We have the following template

```
https://www.glispa.com/join-us.html?search=${name}&callback=https%3A%2F%2Fwww.glispa.com%2F${page}
```

We want to replace the `${name}` and `${page}` placeholders with contextual strings. 
A solution to perform this replacement is using `String.replace`, but there are some additional considerations:
* both placeholders are in the URL query string, so if they contain special characters, they must be encoded
* `${page}` is part of an already encoded sub URL, so it needed double encoding

These transformations of values could be done prior to the string substitutions, 
but the reusability of the macro would be low (what if we want to replace a `${page}` but now part of the URL path)

COMBO is inspired by shell commands. A substitution placeholder is not only an identifier but series of actions combined together.

The previous template implemented with COMBO will look like

```
https://www.glispa.com/join-us.html?search=${param name | escape form}&callback=https%3A%2F%2Fwww.glispa.com%2F${param page | escape form | escape form}
```

* `param` is a macro producing a value based on a key (here `name` and `page`)
* `escape` is a macro encoding special URL character (here `form` means form parameter encoding)
* different macros are piped together to produce the expected result 
 
### The grammar

A macro "chain" always starts with '${' and always end with '}'. It contains a list of macro separated with a '|''
```
chain ::= "${" macro *("|" macro) "}"
```

A macro is composed of an identifier and an optional list of argument separated by space
```
macro ::= identifier *(SP arg)
identifier ::= 1*(<any US-ASCII uppercase letter "A".."Z">
                 | <any US-ASCII lowercase letter "a".."w">
                 | <any US-ASCII digit "0".."9">
                 | "_")
```

An argument value could be anything, but if it contains a macro grammar separator or termination character (i.e " ", "}" or "|"), it must be escaped with a backslash.

Some valid examples
```
${stuff}
${do stuff}
${a | b | c}
${single_macro_with_1 super\ complex\ arg\|\}\ .}
```

## Installation

Add the following dependency to your project

```
<dependency>
    <groupId>com.glispa.combo</groupId>
    <artifactId>combo-core</artifactId>
    <version>1.4.1</version>
</dependency>

```

and implement your own Macro/MacroRegistry or directly use the sample project for URL processing

```
<dependency>
    <groupId>com.glispa.combo</groupId>
    <artifactId>combo-url</artifactId>
    <version>1.4.1</version>
</dependency>

```

## Usage

All the library is build around three main entities

* Macro

 The Macro interface need to be implemented for adding a new macro. The purpose of implementation the class is to describe the logic of replacement.
 It should also provide a MacroFactory that creates the Macro instance for a given set of arguments.

* MacroRegistry

 The MacroRegistry holds a map of Macro implementations indexed by their identifier. It is the single point of truth for registering or getting a
 macro.

* Template

 The template wraps a text containing macro chains and perform the substitutions on demand. It can be used by multiple threads at the same time.

When performing the substitutions, a state object could be provided. Therefore all the previous 3 entities are generic to make the state Class flexible.

The common scenario is :
* Create your own macro classes with factories
* Initialize once the registry and add your macro factories
* Initialize once your templates
* For each request, render the templates with different states

Note that the parser is working only during the template initialization so the rendering is very fast.

```java
UrlMacroRegistry macroRegistry = new UrlMacroRegistry();
Template<Map<String, String>> template = new Template<>(macroRegistry, "...");
/* ... */
Map<String, String> state = ...;
String url = template.render(state);
```

## Contributing

1. Fork the project
2. Create a feature branch (git checkout -b my-new-feature)
3. Commit your changes to the branch (git commit -am 'Added some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request


## License

[MIT License](LICENSE)
