# extended-hash-set
A HashSet data structure designed for fast subset checks.

It extends the Java Hashset class by adding the isSubsetOf, isSupersetOf, isProperSubsetOf, isProperSupersetOf methods.

It is designed for checking subsets of smaller sets of 20 elements or less. Once you go beyond that you will start losing performance drastically.

The algorithm implemented will have false positives but never a false negative. You can tweak the performance by changing the modulus variable in the hashset. The lower the number, the more elements you can have before you start losing performance. However, the higher the number, the less false positives you will get.
