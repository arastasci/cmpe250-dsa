# CMPE250
All of my projects for the Data Structures & Algorithms class in Bogazici University.
## Project1
This project consisted of creating a linked list and applying basic linked list algorithms such as reversing, removing element at index, deleting duplicates.
## Project2
In this project, a BST and AVL tree is implemented separately. Other than elementary operations for trees such as deleting and adding nodes, there are operations to "send a message" between two nodes. In certain types of inputs(such that form very long branches in a BST), you are expected to see the time complexity differences of these operations on BSTs and AVL trees.
## Project3
This project is an implementation of a discrete event simulation for a complex flight system. There are various states for a flight and events are created based on these states, and put into a priority queue.
## Project4
In this project, a graph is given as input and it is expected for you to implement an algorithm for both finding the shortest path from the specified start and finish vertices and minimizing the path of traversing all vertices marked as "flags". For the first algorithm I made a  simple Dijkstra's Shortest Path; for the latter I first made a complete graph consisting only of flags and then found the  MST of the graph using Prim's algorithm.
## Project5
This project expected me to find a way to maximize the flow for multiple sources and a single sink and find the min-cut(the places of bottlenecks). I connected all the sources to a dummy source and applied Ford-Fulkerson's Maximum Flow algorithm. For the min-cut edges, I marked all vertices reachable from the dummy source; then the edges from reachables to non-reachables are the min-cut edges.
