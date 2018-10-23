# Readme

## Execution Conventions

### Programming In Scala

This initiative will define classes, their implementation, operations, etc. Sometimes "sole" functions will be implemented. Since the goal of the former is very broad no single API can cover all the cases. Thus the testing API for these cases will be very unique.

For single functions or the main function of a given task the API can be the same of the [SPOJ](#spoj).

### SPOJ

SPOJ testing is quite unique because the expectation is to read from stdin and write to stdout. Thus the API for this case can be easily conventioned and enforced.

## Code Style Conventions

### Project Wide

This project should follow the Scala community coding conventions.

* ???

If there is none then:

1.  Do not use empty lines immediately before/after new scopes, except on object declaration.

    ```
    def main(args: Array[String]): Unit = {

      ReadApplyPrint(Reader.parseIntsFromFileOrStdin _, filterBefore42 _)

    }
    ```

    Should be:

    ```
    def main(args: Array[String]): Unit = {
      ReadApplyPrint(Reader.parseIntsFromFileOrStdin _, filterBefore42 _)
    }
    ```

### SPOJ

The entry point should be like:

```
object Main extends ScalaInitiativesMainSPOJ {

  def main(args: Array[String]): Unit = {
```
[comment]: # ( vim: set filetype=markdown fileformat=unix wrap: )
