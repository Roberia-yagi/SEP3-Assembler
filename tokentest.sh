find -name "*.java" > sources.txt
javac @sources.txt
java lang/sep3asm/TestSep3asmToken lang/sep3asm/fib.s
