# Projects & SDK Protocol Compatibility

## Table of contents

- [Abstract](#abstract)
- [Protocol Version Compatibilities](#protocol-version-compatibilities)
  - [Icon Status Attribution](#icon-status-attribution)
  - [Elephant: Protocol v0.5.?.?](#)
  - [Dragon: Protocol v0.4.?.?](#)
  - [Cow: Protocol v0.3.0.2](#cow-protocol-v0302)
- [History](#history)

## Abstract

This document aims to group the test progress by features in the Elephant Milestone of the catapult server.  

The document should provide with an easy aggregation of all features addressed in the protocol project during milestones development and should provide a clear compatibility table for individual protocol features / or changes.

## Protocol Version Compatibilities

### Icon Status Attribution

Following table describes the status attributions for each of the icons that will be used in the protocol features compatibility tables:

| Icon | Status |
| :-: | :-: |
| :question: | Investigation/Research is currently in progress. |
| :white_check_mark: | Test automation is complete. |
| :o: | Work in Progress (WIP) |
| :stop_sign: | Test has not started. |

### Elephant: Protocol v0.5.?.?

| feature | Testing |
| :-: |:-: |
| consensus update PoS+ | :o:  |
| enhanced delegated harvesting | :o: |
| metadata key-value | :white_check_mark: |
| restriction account| :white_check_mark: |
| restriction mosaic | :o: |

### Dragon: [Protocol v0.4.?.?](https://github.com/nemtech/catapult-server/milestone/5)

| feature | Testing |
| :-: |:-: |
| hashlock with alias | :white_check_mark: |
| optin cosigners | :white_check_mark: |
| lightning network | :white_check_mark: |
| consensus PoS+ | :white_check_mark:  |
| generation hash | :white_check_mark: |

### Cow: [Protocol v0.3.0.2](https://github.com/nemtech/catapult-server/milestone/3)

| feature | Testing|
| :-: |:-: |
| catbuffer | :white_check_mark:  |
| mosaic/namespace split | :white_check_mark: |
| aliases | :white_check_mark: |
| receipts | :white_check_mark: |
| escrow | :white_check_mark: |
| fees | :o: |
| delegated harvesting | :o: |
| secret locks hash algos | :white_check_mark: |
| hashlock | :white_check_mark: |
| merkle proofs | :stop_sign: |
| rollback | :o: |
| multiSig account | :white_check_mark: |
| namespace | :white_check_mark: |
| asset | :white_check_mark: |
| transfer | :white_check_mark: |


## History

| **Date**      | **Version**   |
| ------------- | ------------- |
| Jul 14 2019   | Initial Draft |
