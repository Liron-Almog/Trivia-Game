
## video
https://github.com/Liron-Almog/Trivia-Game/assets/100926289/44bcb214-f8ca-499d-8d9c-8b41e10afa7c


## Description

I have created a trivia game. The goal of the player is to answer as
many questions as possible without making mistakes and to be ranked at least
in the top ten in order to appear on the leaderboard. As soon as the player is already registered in the 
score table, we check if their new score is higher than the previous one before adding it. If the new score is
indeed higher, we update the entry with the new score. Therefore, each user will have only one entry in
the table if they have played at least once.
To participate in the game, 
players need to register through the login system. Players who have not registered 
will not be able to play the game. The admin has the ability to add questions to the game,
remove them, and also delete users from the leaderboard. I have created two tables. The first
table stores the username and score of each player, while the second table stores the questions.
Each record in the questions table consists of a question, the 
correct answer, and three incorrect answers.

### General information

Once the user/admin logs in, they are unable to navigate back to the following screens: Login, 
Registration, and Home. In order to access these pages, they must log out first.
I have created two tables. The first table stores the username and score of each player,
while the second table stores the questions.
Each record in the second table consists of a question, the correct answer, and three incorrect answers.
There are validations in each form in the game.

## Installation

In order to create a database,
need to open a scheme called ex5 in PhpAdmin

## Useful information

During the game startup, I have created two users.
1. name:admin password:password
2. name:user password:password
