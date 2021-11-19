# ChipTextField

![](/images/screenshot_light.png)

![](/images/screenshot_dark.png)

![](/images/screenshot_checkable.png)

![](/images/screenshot_avatar.png)



# Usage

```groovy
implementation "io.github.dokar3:chiptextfield:0.1.0"
```

```kotlin
val state = rememberChipTextFieldState<TextChip>()
ChipTextField(
    state = state,
    onCreateChip = { text -> TextChip(text) }
)
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