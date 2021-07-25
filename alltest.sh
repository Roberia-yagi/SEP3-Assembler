find -name "*.java" > sources.txt
javac @sources.txt
java lang/sep3asm/Sep3assembler lang/sep3asm/fib.s
