# ChipTextField

![](/images/screenshot_light.png)

![](/images/screenshot_dark.png)

![](/images/screenshot_checkable.png)

![](/images/screenshot_avatar.png)



# Usage

```kotlin
val state = rememberChipTextFieldState<TextChip>()
ChipTextField(
    state = state,
    onCreateChip = { text -> TextChip(text) }
)
```

