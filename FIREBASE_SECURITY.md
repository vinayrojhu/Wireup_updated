# Firebase security deployment

The repository now contains Firestore and Storage rules, wired through `firebase.json`.

Before deploying, validate them against the Firebase Emulator Suite and the production
data schema. Comments now live at `tweets/{tweetId}/comments/{commentId}`. This avoids
granting arbitrary users write access to an entire tweet document. Existing comments
stored in the legacy `tweets.comments` array must be migrated to that subcollection
before this version reaches production.

The rules permit a signed-in user to add only their own ID to a comment's `likes` array
or another user's `followers` array. They do not permit arbitrary mutation of either
array. User-uploaded post images are now stored at `images/{uid}/{imageId}`, which
allows Storage rules to prevent one user writing to another user's path. App Check must
be enabled in Firebase Console before production rollout.

Deploy only after review with:

`firebase deploy --project wireup-fa74b --only firestore:rules,storage`

Also enable Firebase App Check in the Firebase console for Authentication, Firestore,
and Storage, and restrict the Android API key to this application's package and signing
certificate in Google Cloud.
