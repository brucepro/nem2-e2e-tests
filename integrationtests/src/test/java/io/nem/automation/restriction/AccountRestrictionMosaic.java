package io.nem.automation.restriction;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.nem.automation.asset.AssetRegistration;
import io.nem.automation.common.BaseTest;
import io.nem.automationHelpers.common.TestContext;
import io.nem.automationHelpers.helper.AccountRestrictionHelper;
import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.mosaic.MosaicId;
import io.nem.sdk.model.mosaic.UnresolvedMosaicId;
import io.nem.sdk.model.transaction.AccountRestrictionType;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRestrictionMosaic extends BaseTest {
    private final AccountRestrictionHelper accountRestrictionHelper;

    /**
     * Constructor.
     *
     * @param testContext Test context.
     */
    AccountRestrictionMosaic(final TestContext testContext) {
        super(testContext);
        accountRestrictionHelper = new AccountRestrictionHelper(testContext);
    }

    @Given("^(\\w+) has the following assets registered and active:$")
    public void theFollowingAssetsAreRegisteredAndActive(final String userName, final List<String> assets) {
        final AssetRegistration assetRegistration = new AssetRegistration(getTestContext());
        // Alice already has cat.currency registered to her. What happens if we try to register again?
        assets.parallelStream().forEach(asset -> {
                assetRegistration.registerAsset(userName, asset);
        });
        getTestContext().getLogger().LogInfo(getAccountInfoFromContext(userName).toString());

    }

/*
    /**
     * Gets the Account from userName and adds the specified restriction to the account
     *
     * @param username
     * @param restrictionOperation
     * @param restrictedItemType
     * @param restrictedItems
     * /
    @When("^(\\w+) (allows|blocks) receiving transactions containing the following " +
            "(assets?|addresses):$")
    public void allowsOrBlocksReceivingTransactionsContainingTheFollowingItems(
            final String username, final String restrictionOperation, final String restrictedItemType,
            final List<String> restrictedItems) {
        final Account signerAccount = getUser(username);
        final List<Object> restrictedItemsList = new ArrayList<>();
        final AccountRestrictionType accountRestrictionType = accountRestrictionHelper.getAccountRestrictionType(
                restrictionOperation, restrictedItemType);
        getTestContext().getLogger().LogInfo("AccountRestrictionType = %s", accountRestrictionType.toString());
        if (restrictedItemType.equalsIgnoreCase("addresses")) {
            restrictedItems.forEach(user -> restrictedItemsList.add(getUser(user).getAddress()));
        } else if (restrictedItemType.matches("^assets?")) {
            restrictedItems.forEach(asset -> restrictedItemsList.add(resolveMosaicId(asset)));
        } else {
            restrictedItemsList.addAll(restrictedItems);
        }
        accountRestrictionHelper.addAppropriateModificationTransactionAndWait(restrictedItemType,
                restrictedItemsList, signerAccount, accountRestrictionType);
        // setting recipient since one who blocks/allows will most probably be the recipient when testing
        getTestContext().getScenarioContext().setContext("recipient", username);
    }
*/

    @When("^(\\w+) allows receiving transactions containing the following assets:$")
    public void allowsReceivingTransactionsContainingTheFollowingAssets(
            final String username, final List<String> allowedAssets) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        allowedAssets.forEach(asset -> additions.add(resolveMosaicId(asset)));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndWait(
                signerAccount, AccountRestrictionType.ALLOW_INCOMING_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) blocks receiving transactions containing the following assets:$")
    public void blocksReceivingTransactionsContainingTheFollowingAssets(
            final String username, final List<String> blockedAssets) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        blockedAssets.forEach(asset -> additions.add(resolveMosaicId(asset)));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndWait(
                signerAccount, AccountRestrictionType.BLOCK_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) removes the restriction on the following allowed assets:$")
    public void removesRestrictionOnAllowedAssets(
            final String username, final List<String> allowedAssets) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        allowedAssets.forEach(asset -> deletions.add(resolveMosaicId(asset)));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndWait(
                signerAccount, AccountRestrictionType.ALLOW_INCOMING_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) removes the restriction on the following blocked assets:$")
    public void removesRestrictionOnBlockedAssets(
            final String username, final List<String> blockedAssets) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        blockedAssets.forEach(asset -> deletions.add(resolveMosaicId(asset)));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndWait(
                signerAccount, AccountRestrictionType.BLOCK_MOSAIC, additions, deletions);
    }

/*
    @When("^(\\w+) removes (allowed|blocked) receiving the following " +
            "(assets?|addresses|transaction types?):$")
    public void removesAllowedOrBlockedReceivingTransactionsContainingTheFollowingItems(
            final String username, final String restrictionOperation, final String restrictedItemType,
            final List<String> restrictedItems) {
        final Account signerAccount = getUser(username);
        final List<Object> restrictedItemsList = new ArrayList<>();
        final AccountRestrictionType accountRestrictionType = accountRestrictionHelper.getAccountRestrictionType(
                restrictionOperation.equals("allowed") ? "allows" : "blocks", restrictedItemType);
        if (restrictedItemType.equals("addresses")) {
            restrictedItems.parallelStream().forEach(user -> restrictedItemsList.add(getUser(user).getAddress()));
        } else if (restrictedItemType.matches("^assets?")) {
            restrictedItems.forEach(asset -> restrictedItemsList.add(resolveMosaicId(asset)));
        } else {
            restrictedItemsList.addAll(restrictedItems);
        }
        accountRestrictionHelper.removeAppropriateModificationTransactionAndWait(restrictedItemType,
                restrictedItemsList, signerAccount, accountRestrictionType);
        // setting recipient since one who blocks/allows will most probably be the recipient when testing
        getTestContext().getScenarioContext().setContext("recipient", username);
    }
*/

    @When("^(\\w+) unblocks ([^\"]*) asset$")
    public void unblocksGivenAsset(final String userName, final String asset) {
        this.removesRestrictionOnBlockedAssets(userName, new ArrayList<>(Collections.singletonList(asset)));
    }

/*
    @And("^receiving \"([^\"]*)\" assets should remain blocked$")
    public void receivingAssetsShouldRemainBlocked(final String assetType) {
        // This step is basically calling the two below steps
        //    When Alice tries to send 1 asset "ticket" to Bob
        //    And Alice should receive the error "Failure_RestrictionAccount_Mosaic_Transfer_Prohibited"
        final SendAsset sendAsset = new SendAsset(getTestContext());
        final String recipient = getTestContext().getScenarioContext().getContext("recipient");
        sendAsset.triesToTransferAsset(BaseTest.AUTOMATION_USER_ALICE, BigInteger.ONE, assetType, recipient);
        final SendTransaction sendTransaction = new SendTransaction(getTestContext());
        sendTransaction.verifyTransactionError(BaseTest.AUTOMATION_USER_ALICE,
                "Failure_RestrictionAccount_Mosaic_Transfer_Prohibited");
    }
*/

/*
    @Given("^(\\w+) has only allowed receiving the following assets:$")
    public void onlyAllowedReceivingAssets(final String username, final List<String> assets) {
        this.allowsReceivingTransactionsContainingTheFollowingAssets(username, assets);
    }
*/

    @When("^(\\w+) removes ([^\"]*) from allowed assets$")
    public void removesFromTheAllowedAssets(final String username, final String asset) {
        this.removesRestrictionOnAllowedAssets(username, new ArrayList<>(Collections.singletonList(asset)));
    }

    @Given("^(\\w+) has blocked receiving ([^\"]*) assets$")
    public void hasBlockedReceivingAssets(final String username, final String asset) {
        this.blocksReceivingTransactionsContainingTheFollowingAssets(username, new ArrayList<>(Collections.singletonList(asset)));
    }

    @Given("^(\\w+) has only allowed receiving ([^\"]*) assets$")
    public void hasOnlyAllowedReceivingAssets(final String username, final String asset) {
        this.allowsReceivingTransactionsContainingTheFollowingAssets(username, new ArrayList<>(Collections.singletonList(asset)));
    }

    @When("^(\\w+) tries to unblock receiving ([^\"]*) assets$")
    public void triesToUnblockReceivingAssets(final String username, final String asset) {
        final Account signerAccount = getUser(username);
        List<UnresolvedMosaicId> additions = new ArrayList<>();
        List<UnresolvedMosaicId> deletions = new ArrayList<>();
        deletions.add(resolveMosaicId(asset));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                AccountRestrictionType.BLOCK_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) tries to block receiving ([^\"]*) assets$")
    public void triesToBlockReceivingAssets(final String username, final String asset) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        additions.add(resolveMosaicId(asset));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                AccountRestrictionType.BLOCK_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) tries to only allow receiving ([^\"]*) assets$")
    public void triesToOnlyAllowReceivingAssets(final String username, final String asset) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        additions.add(resolveMosaicId(asset));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                AccountRestrictionType.ALLOW_INCOMING_MOSAIC, additions, deletions);
    }

    @When("^(\\w+) tries to remove ([^\"]*) from allowed assets$")
    public void triesToRemoveFromAllowedAssets(final String username, final String asset) {
        final Account signerAccount = getUser(username);
        final List<UnresolvedMosaicId> additions = new ArrayList<>();
        final List<UnresolvedMosaicId> deletions = new ArrayList<>();
        deletions.add(resolveMosaicId(asset));
        accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                AccountRestrictionType.ALLOW_INCOMING_MOSAIC, additions, deletions);
    }

    @Given("^(\\w+) has already (allowed|blocked) receiving (\\d+) different assets$")
    public void hasAllowedOrBlockedReceivingDifferentAssets(final String username, final String restrictionType, final int count) {
        // first register assets to another user than the given username.
        this.userHasGivenNumberOfDifferentAssetsRegisteredAndActive(username, count);
        List<String> assets = getTestContext().getScenarioContext().getContext("randomAssetsList");
        //TODO: figure out how to confirm Alex is the correct user to use
        if (restrictionType.equalsIgnoreCase("allowed")) {
            this.allowsReceivingTransactionsContainingTheFollowingAssets(username, assets);
        } else {
            this.blocksReceivingTransactionsContainingTheFollowingAssets(username, assets);
        }
    }

    @When("^(\\w+) tries to (add|delete) more than (\\d+) restrictions in a transaction$")
    public void userTriesToAddOrDeleteTooManyRestrictionsInATransaction(final String username, final String addOrDelete, final int count) {
        final Account signerAccount = getUser(username);
        List<UnresolvedMosaicId> modifications = new ArrayList<>();
        List<String> assets = getTestContext().getScenarioContext().getContext("randomAssetsList");
        //TODO: assuming that at least count + 1 assets are registered. May be better to check and throw if not.
        assets.stream().limit(count + 1).collect(Collectors.toList()).parallelStream().forEach(asset -> {
            MosaicId mosaicId = resolveMosaicId(asset);
            modifications.add(mosaicId);
        });
        if (addOrDelete.equalsIgnoreCase("add")) {
            accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                    AccountRestrictionType.BLOCK_MOSAIC, modifications, new ArrayList<>());
        } else if (addOrDelete.equalsIgnoreCase("delete")) {
            accountRestrictionHelper.createAccountMosaicRestrictionTransactionAndAnnounce(signerAccount,
                    AccountRestrictionType.BLOCK_MOSAIC, new ArrayList<>(), modifications);
        }

    }

    @Given("^(\\w+) has (\\d+) different assets registered and active$")
    public void userHasGivenNumberOfDifferentAssetsRegisteredAndActive(final String username, final int count) {
        List<String> assets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) assets.add(RandomStringUtils.randomAlphanumeric(10));
        this.theFollowingAssetsAreRegisteredAndActive(username, assets);
        getTestContext().getScenarioContext().setContext("randomAssetsList", assets);
    }
}