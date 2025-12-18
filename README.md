# Blocky Game

_(Gamewerks corporation internal code—do not share!)_

## Credits

Primary developer: David Lee, Aidan Michaelson

## Revsion log (Revision for resubmision)

- Fixed functionality so that the block would not break if it went to far right
- Fixed it so that rotation would not clip into other pieces
- Game runs fine and ends when it hits top

### Resources Used

+ Professor Osera helped us figure out the remaining errors for resubmission

## Changelog

- Fixed weird edge case where active piece was null
- Fixed rotation
- Fixed game crashing error when block moves to far right
- Fixed Array out of index error
- fixed delete function so that it shifts the top row down once row is deleted
- Implemented Yates Shuffle so that blocks are shuffled after 7 are used
- Fixed locking problems and process gravity
- Changed bounds of height in gfx settings so that the block doesn't go off screen
- Found index out of bounds error crashing game before it began

