^!+t::
{
    SetKeyDelay 60      ; Wait 60ms between each test keystroke.

    SendEvent "blah{enter}-5{enter}5{enter}1{enter}1{enter}1{enter}5{enter}2{enter}2{enter}2{enter}2{enter}2{enter}5{enter}3{enter}5{enter}4{enter}4{enter}5{enter}1{enter}5{enter}7{enter}6{enter}blah{enter}-1{enter}blah{enter}7{enter}0{enter}-5{enter}blah{enter}2{enter}6{enter}name{enter}99{enter}9{enter}1{enter}7{enter}8{enter}7{enter}8{enter}"

}               ; Enter test keystrokes with: Ctrl + Alt + Shift + T

Pause::Pause -1 ; Pause & resume script with: Pause/Break

^!+r::Reload    ; Reload script with: Ctrl + Alt + Shift + R
