# ExpressionParser
Arithmetic expression parser with unlimited number of variables

# Available arithmetic operations:
  `+` 
  `-`
  `/`
  `*`
  `% (mod)`
  `^ (pow)`
  `// (log)`
# Variables
The variable name must contain only `Latin letters`, `:`, `_`, `%`
# To use:
```
ExpressionParser parser = new ExpressionParser();
Expression expr = parser.parse(yourExpression);
```
`expr.evaluate(x1, x2, x3, ...)` to evaluate expression with variables values `x1, x2, x3`.
The numbers are substituted in the order in which they are found in the string

`expr.getVariables()` to get the variable names in the order in which they are found in the string.

`expr.toString()` to get full brackets form of expression

`expr.toMiniString()` to get a simplified form of the expression
# Example:
```
expr = parser.parse("((x)) ^ 2 + 5.6 % 2 + 5");
System.out.println(expr.evaluate(5));
System.out.println(expr.getVariables());
System.out.println(expr.toString());
System.out.println(expr.toMiniString());
```

Output:

```
31.6
[x]
(((x^2.0)+(5.6%2.0))+5.0)
x^2.0+5.6%2.0+5.0
```
```
expr = parser.parse("owner:PLAYER_DAMAGE * (1 - 0.00667 * target:PLAYER_DEFENSE / (1 +  0.00667 * target:PLAYER_DEFENSE))");
for (int def = 10; def < 100; def += 10) {
    for (int dmg = 10; dmg < 100; dmg += 10) {
        System.out.print(Double.toString(expr.evaluate(dmg, def, def)).substring(0, 4));
        System.out.print("   ");
    }
    System.out.println();
}
System.out.println(expr.getVariables());
```

Output:
```
  9.37   18.7   28.1   37.4   46.8   56.2   65.6   74.9   84.3   
  8.82   17.6   26.4   35.2   44.1   52.9   61.7   70.5   79.4   
  8.33   16.6   24.9   33.3   41.6   49.9   58.3   66.6   74.9   
  7.89   15.7   23.6   31.5   39.4   47.3   55.2   63.1   71.0   
  7.49   14.9   22.4   29.9   37.4   44.9   52.4   59.9   67.4   
  7.14   14.2   21.4   28.5   35.7   42.8   49.9   57.1   64.2   
  6.81   13.6   20.4   27.2   34.0   40.9   47.7   54.5   61.3   
  6.52   13.0   19.5   26.0   32.6   39.1   45.6   52.1   58.6   
  6.24   12.4   18.7   24.9   31.2   37.4   43.7   49.9   56.2   
  [owner:PLAYER_DAMAGE, target:PLAYER_DEFENSE, target:PLAYER_DEFENSE]
```
