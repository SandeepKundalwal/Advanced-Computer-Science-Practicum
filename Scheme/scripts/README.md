# Scheme Functional Programming Language
Scheme's Functional Sonata: Composing Procedures, Sorting Harmonies, and Root Explorations

In this assignment, I did three questions using Scheme which is a Functional Programming Language:
1. (a) Define a procedure twice that takes a procedure of one argument as argument and returns a procedure that applies the original procedure twice. For example, if inc is a procedure that adds 1 to its argument, then (twice inc) should return a procedure that adds 2.\
   (b) Define a procedure compose that implements function composition. For example, if inc is defined as in part (a), then ((compose square inc) 4) should return 25.\
   (c) Can you define twice using compose?
2. Write a procedure merge that takes two sorted lists and merges them to return another list that contains unique elements from both the lists in sorted order (assume ascending order for all). Now use this merge procedure to write a merge-sort procedure that takes a list as argument, and returns its sorted version.
3. Recall whom did we agree to have invented perhaps the largest number of things named after him. Apart from the square-root method (using good-enough, improve, and an initial guess), he had also invented a method to compute cube-roots. The method is based on the fact that if y is an approximation to the cube root of x, then a better approximation is given by:
            (x/y2 + 2y) / 3
   Use this formula to implement a cube-root function analogous to the square-root procedure.
