# ChipTextField

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.dokar3/chiptextfield/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.dokar3/chiptextfield)

![](/images/screenshot_dark.png)


![](/images/screenshot_light.png)

![](/images/screenshot_checkable.png)

![](/images/screenshot_avatar.png)



# Usage

```groovy
implementation "io.github.dokar3:chiptextfield:latest_version"
```

### Default chips

```kotlin
val state = rememberChipTextFieldState<Chip>()
ChipTextField(state = state, onCreateChip = ::Chip)
```

### Custom chips

`chipStartWidget` and `chipEndWidget` are provided to implement custom chips.

**Checkable chips**

```kotlin
class CheckableChip(text: String, isChecked: Boolean = false) : Chip(text) {
    var isChecked by mutableStateOf(isChecked)
}

val state = rememberChipTextFieldState<CheckableChip>()

ChipTextField(
        state = state,
        onCreateChip = ::CheckableChip,
        chipStartWidget = { chip -> CheckIcon(chip) }, // Show check icon if checked
        chipEndWidget = {}, // Hide default close button
        onChipClick = { chip -> chip.isChecked = !chip.isChecked }
)

@Composable
fun CheckIcon(chip: CheckableChip) { ... }
```

**Avatar chips**

```kotlin
class AvatarChip(text: String, val avatarUrl: String) : Chip(text)

val state = rememberChipTextFieldState<AvatarChip>()

ChipTextField(
        state = state,
        onCreateChip = { text -> AvatarChip(text, avatarUrl) },
        chipStartWidget = { chip -> Avatar(chip) } // Load and display avatar
)

@Composable
fun Avatar(chip: AvatarChip) { ... }
```



# License

```
Copyright 2021 dokar3

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```