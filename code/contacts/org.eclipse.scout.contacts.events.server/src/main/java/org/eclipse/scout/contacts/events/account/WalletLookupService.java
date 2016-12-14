package org.eclipse.scout.contacts.events.account;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.contacts.events.account.model.Account;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class WalletLookupService extends AbstractLookupService<String> implements IWalletLookupService {

  private String personId = null;

  public String getPersonId() {
    return personId;
  }

  @Override
  public void setPersonId(String personId) {
    this.personId = personId;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
    ArrayList<LookupRow<String>> rows = new ArrayList<>();

    Account wallet = BEANS.get(EthereumService.class).getWallet(call.getKey());
    rows.add(new LookupRow<>(wallet.getAddress(), wallet.getName()));

    return rows;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
    // TODO [mzi] Auto-generated method stub.
    return null;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
    ArrayList<LookupRow<String>> rows = new ArrayList<>();
    String searchText = call.getText().toLowerCase();

    BEANS.get(EthereumService.class).getWallets(getPersonId())
        .stream()
        .forEach(address -> {
          Account wallet = BEANS.get(EthereumService.class).getWallet(address);
          if (wallet.getName().toLowerCase().contains(searchText)) {
            rows.add(new LookupRow<>(wallet.getAddress(), wallet.getName()));
          }
        });

    return rows;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
    ArrayList<LookupRow<String>> rows = new ArrayList<>();

    BEANS.get(EthereumService.class).getWallets()
        .stream()
        .forEach(address -> {
          Account wallet = BEANS.get(EthereumService.class).getWallet(address);
          rows.add(new LookupRow<>(wallet.getAddress(), wallet.getName()));
        });

    return rows;
  }
}