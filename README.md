maze
====

This project implements a maze solver.

Name: Evan Kenyon

### Timeline

Start Date: 9/8/21

Finish Date: 9/13/21

Hours Spent: 8


### Tutorial and other Resources Used
No tutorials or other resources used.


### Resource Attributions
The only resource I used for this project was the lab_maze course gitlab repo. Specifically,
I started refactoring the code with a partner, Amr Tagel-Din, in lab, and afterwards continued
to do so (specifically, I brought up member variables and methods to SearchAlgorithm in lab and afterwards, 
and created QueueSearchAlgorithm afterwards).

### Running the Program

Main class: Main

Data files needed: none

Key/Mouse inputs: 
* N is to create a new maze
* S is to step through the maze solver (must pause first)
* P is to pause the maze solver
* D is to start a DFS maze solver
* B is to start a BFS maze solver
* G is to start a Greedy maze solver
* R is to start a RandomWalk maze solver
* M is to start a Magic maze solver

Known Bugs: None


### Notes/Assumptions
I found the requirement to create a public method that returns the current number of backtracks that have
had to be made a bit ambiguous. It was difficult to tell what counted as a backtrack in Greedy and BFS,
so I made the assumption that whenever next was not null after next had been not null (there was a member
variable that was set to false whenever next was not null and then set to true the next time next was null)
that that counted as a backtrack (since this was the case for the other search algorithms).

### Impressions
Overall, I thought this assignment was very helpful. I did not have to put as much time into it compared to 
the other projects thusfar to still learn a lot about a key concept. The only thing that I think could've been
better was the ambiguity with number of backtracks for BFS and Greedy as mentioned above. However, I still think
that this was somewhat positive since it forced me to make an assumption based on the information I had, which 
usually I have not had to do while coding.

